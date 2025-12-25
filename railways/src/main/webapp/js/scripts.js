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
