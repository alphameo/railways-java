-- ==========================================
-- LOCOMOTIVES
-- ==========================================
INSERT INTO locomotive (id, number, model) VALUES
(UUID(), 'L001', 'Siemens Vectron'),
(UUID(), 'L002', 'GE Evolution'),
(UUID(), 'L003', 'Alstom Prima'),
(UUID(), 'L004', 'Bombardier TRAXX'),
(UUID(), 'L005', 'EMD SD70ACe');

-- ==========================================
-- TRAIN COMPOSITIONS
-- ==========================================
INSERT INTO train_composition (id, locomotive_id) VALUES
(UUID(), (SELECT id FROM locomotive WHERE number='L001')),
(UUID(), (SELECT id FROM locomotive WHERE number='L002')),
(UUID(), (SELECT id FROM locomotive WHERE number='L003')),
(UUID(), (SELECT id FROM locomotive WHERE number='L004')),
(UUID(), (SELECT id FROM locomotive WHERE number='L005'));

-- ==========================================
-- TRAINS
-- ==========================================
INSERT INTO train (id, train_composition_id, number) VALUES
(UUID(), (SELECT id FROM train_composition LIMIT 1 OFFSET 0), 'T001'),
(UUID(), (SELECT id FROM train_composition LIMIT 1 OFFSET 1), 'T002'),
(UUID(), (SELECT id FROM train_composition LIMIT 1 OFFSET 2), 'T003'),
(UUID(), (SELECT id FROM train_composition LIMIT 1 OFFSET 3), 'T004'),
(UUID(), (SELECT id FROM train_composition LIMIT 1 OFFSET 4), 'T005');

-- ==========================================
-- CARRIAGES
-- ==========================================
INSERT INTO carriage (id, number, train_composition_id, position, content_type, capacity) VALUES
(UUID(), 'C001', (SELECT id FROM train_composition LIMIT 1 OFFSET 0), 1, 'passenger', 80),
(UUID(), 'C002', (SELECT id FROM train_composition LIMIT 1 OFFSET 0), 2, 'passenger', 80),
(UUID(), 'C003', (SELECT id FROM train_composition LIMIT 1 OFFSET 1), 1, 'cargo', 10000),
(UUID(), 'C004', (SELECT id FROM train_composition LIMIT 1 OFFSET 2), 1, 'passenger', 60),
(UUID(), 'C005', (SELECT id FROM train_composition LIMIT 1 OFFSET 2), 2, 'passenger', 60);

-- ==========================================
-- GENERATE 1000 CARRIAGES (OFFSET FOR UNIQUENESS)
-- ==========================================
-- Find the max existing number (extract numeric part)
SET @max_num := (
  SELECT COALESCE(MAX(CAST(SUBSTRING_INDEX(number, 'C', -1) AS UNSIGNED)), 0)
  FROM carriage
  WHERE number LIKE 'C%'
);
-- Generate sequence 1..1000, offset by max_num
DROP TEMPORARY TABLE IF EXISTS seq;
CREATE TEMPORARY TABLE seq (n INT PRIMARY KEY);
SET @i := 0;
INSERT INTO seq (n)
SELECT @i := @i + 1
FROM information_schema.columns c1
CROSS JOIN information_schema.columns c2
LIMIT 1000;
-- Insert carriages with offset unique numbers, NULL composition/position
INSERT INTO carriage (id, number, train_composition_id, position, content_type, capacity)
SELECT
  UUID(),
  CONCAT('C', LPAD(n + @max_num, 5, '0')),  -- Offset to avoid duplicates
  NULL,  -- train_composition_id
  NULL,  -- position
  CASE MOD(n-1, 2)
    WHEN 0 THEN 'passenger'
    ELSE 'cargo'
  END,
  CASE MOD(n-1, 2)
    WHEN 0 THEN FLOOR(RAND() * 51) + 50  -- 50-100 for passenger
    ELSE FLOOR(RAND() * 15001) + 5000  -- 5000-20000 for cargo
  END
FROM seq;
-- Cleanup
DROP TEMPORARY TABLE seq;

-- ==========================================
-- STATIONS
-- ==========================================
INSERT INTO station (id, name, location) VALUES
(UUID(), 'Central Station', 'City Center'),
(UUID(), 'North Station', 'Uptown'),
(UUID(), 'East Station', 'Eastside'),
(UUID(), 'South Station', 'Downtown'),
(UUID(), 'West Station', 'West End'),
(UUID(), 'Airport Station', 'Airport'),
(UUID(), 'Harbor Station', 'Seaport'),
(UUID(), 'Industrial Station', 'Industrial Zone');

-- ==========================================
-- LINES
-- ==========================================
INSERT INTO line (id, name) VALUES
(UUID(), 'Main Line'),
(UUID(), 'Coastal Line'),
(UUID(), 'Airport Express');

-- ==========================================
-- LINE_STATION MAPPINGS
-- ==========================================
INSERT INTO line_station (line_id, station_id, position)
SELECT l.id, s.id, ROW_NUMBER() OVER () AS pos
FROM line l
JOIN station s ON 1=1
WHERE l.name = 'Main Line'
LIMIT 5;

-- ==========================================
-- SCHEDULE ENTRIES (1000 rows)
-- ==========================================
-- Generate 1000 entries (example uses MySQL 8+ syntax)
-- Generate 1000 valid schedule_entry rows
-- ============================
-- 1) build a sequence 1..1000
-- ============================
DROP TEMPORARY TABLE IF EXISTS seq_numbers;
CREATE TEMPORARY TABLE seq_numbers (n INT PRIMARY KEY);

SET @i := 0;
INSERT INTO seq_numbers (n)
SELECT @i := @i + 1
FROM information_schema.columns c1
CROSS JOIN information_schema.columns c2
LIMIT 1000;

-- ============================
-- 2) build indexed train / station lookup tables
--    (so we can cycle through them deterministically)
-- ============================
DROP TEMPORARY TABLE IF EXISTS train_idx;
SET @r := 0;
CREATE TEMPORARY TABLE train_idx AS
SELECT (@r := @r + 1) AS idx, t.id
FROM train t
ORDER BY t.id;

DROP TEMPORARY TABLE IF EXISTS station_idx;
SET @s := 0;
CREATE TEMPORARY TABLE station_idx AS
SELECT (@s := @s + 1) AS idx, st.id
FROM station st
ORDER BY st.id;

-- counts (used to cycle)
SET @train_count  := (SELECT COUNT(*) FROM train_idx);
SET @station_count := (SELECT COUNT(*) FROM station_idx);

-- basic safety check (optional): make sure we have at least one train and station
SELECT
  @train_count  AS train_count,
  @station_count AS station_count;

-- ============================
-- 3) produce 1000 candidate rows, cycling trains & stations
--    arrival/departure are 1 hour apart and increase with sequence
-- ============================
DROP TEMPORARY TABLE IF EXISTS to_insert;
CREATE TEMPORARY TABLE to_insert AS
SELECT
  UUID()                                            AS id,
  ti.id                                            AS train_id,
  si.id                                            AS station_id,
  DATE_ADD('2025-01-01 06:00:00', INTERVAL sn.n HOUR) AS arrival_time,
  DATE_ADD('2025-01-01 07:00:00', INTERVAL sn.n HOUR) AS departure_time,
  sn.n                                             AS seq_n
FROM seq_numbers sn
JOIN train_idx ti ON ti.idx = ((sn.n - 1) % @train_count) + 1
JOIN station_idx si ON si.idx = ((sn.n - 1) % @station_count) + 1
LIMIT 1000;

-- ============================
-- 4) compute order_number per train (row_number partitioned by train_id)
--    using user variables so (train_id, order_number) will be unique
-- ============================
DROP TEMPORARY TABLE IF EXISTS to_insert_ordered;
SET @prev_train := NULL;
SET @rownum := 0;

CREATE TEMPORARY TABLE to_insert_ordered AS
SELECT
  id,
  train_id,
  station_id,
  arrival_time,
  departure_time,
  (@rownum := IF(@prev_train = train_id, @rownum + 1, 1)) AS order_number,
  (@prev_train := train_id) AS prev_marker
FROM to_insert
ORDER BY train_id, seq_n;

-- Optional: inspect distribution per train before insert
-- SELECT train_id, MIN(order_number) AS min_ord, MAX(order_number) AS max_ord, COUNT(*) AS rows_per_train
-- FROM to_insert_ordered GROUP BY train_id;

-- ============================
-- 5) final insert (only the needed columns)
-- ============================
INSERT INTO schedule_entry (id, train_id, station_id, arrival_time, departure_time, order_number)
SELECT id, train_id, station_id, arrival_time, departure_time, order_number
FROM to_insert_ordered;

-- ============================
-- 6) cleanup temporary helper tables (optional)
-- ============================
DROP TEMPORARY TABLE IF EXISTS seq_numbers;
DROP TEMPORARY TABLE IF EXISTS train_idx;
DROP TEMPORARY TABLE IF EXISTS station_idx;
DROP TEMPORARY TABLE IF EXISTS to_insert;
DROP TEMPORARY TABLE IF EXISTS to_insert_ordered;
