document.addEventListener('DOMContentLoaded', function() {
    const cells = document.querySelectorAll('#sudoku-board td');
    let selectedCell = null;

    cells.forEach(cell => {
        cell.addEventListener('click', function() {
            if (selectedCell) {
                selectedCell.classList.remove('selected');
            }

            selectedCell = this;
            selectedCell.classList.add('selected');
        });
    });

    document.addEventListener('keydown', function(event) {
        if (selectedCell) {
            const keyCode = event.keyCode;

            if (keyCode >= 49 && keyCode <= 57 || keyCode === 8) {
                selectedCell.textContent = String.fromCharCode(keyCode);
                if (keyCode === 8) {
                    selectedCell.textContent = '';
                }
                selectedCell.classList.remove('selected');
                selectedCell.classList.add('new-number');
                selectedCell = null;

                const sudokuGrid = [];
                cells.forEach((cell, index) => {
                    const row = Math.floor(index / 9);
                    const col = index % 9;
                    sudokuGrid[row] = sudokuGrid[row] || [];
                    sudokuGrid[row][col] = parseInt(cell.textContent) || 0;
                });

                fetch('/sudoku/update', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(sudokuGrid)
                })
                .then(response => {
                    if (response.ok) {
                        console.log('Sudoku grid updated successfully.');
                    } else {
                        console.error('Failed to update Sudoku grid:', response.statusText);
                    }
                })
                .catch(error => {
                    console.error('Error updating Sudoku grid:', error);
                });
            }
        }
    });
});
