async function delFrom(id, entities) {
    if (!confirm('Are you sure you want to delete?')) return;
    try {
        const response = await fetch(
            `${window.location.origin}/${entities}/${id}`,
            { method: 'DELETE' }
        );
        if (!response.ok) {
            console.log('Delete failed: ' + response.statusText);
        }
    } catch (error) {
        console.log('Error: ' + error.message);
    }
    window.location.href = `${window.location.origin}/${entities}`;
}

let positionCount;

async function loadPosCount(count) {
    console.log(count);
    positionCount = count;
}

function addPosition() {
    const container = document.getElementById('stationOrder');
    const div = document.createElement('div');
    div.className = 'position';
    const options = document.getElementById('stationOptions').innerHTML;
    div.innerHTML = `
        <label>Position ${positionCount + 1}:</label>
        <select name="position${positionCount}">
            <option value="">-- Select Station --</option>
            ${options}
        </select>
        <button type="button" onclick="removePosition(${positionCount})">Remove</button>
    `;
    container.appendChild(div);
    positionCount++;
}

function removePosition(index) {
    const container = document.getElementById('stationOrder');
    const positions = container.querySelectorAll('.position');
    if (positions.length > 0) {
        positions[index].remove();
        // Renumber the remaining positions
        const remaining = container.querySelectorAll('.position');
        remaining.forEach((pos, i) => {
            pos.querySelector('label').textContent = `Position ${i + 1}:`;
            pos.querySelector('select').name = `position${i}`;
            pos.querySelector('button').onclick = () => removePosition(i);
        });
        positionCount--;
    }
}
