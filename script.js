document.addEventListener('DOMContentLoaded', function() {
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
        registerForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const formData = new FormData(this);
            const confirmPassword = document.getElementById('confirmPassword').value;
            if (formData.get('password') !== confirmPassword) {
                alert('Passwords do not match. Please try again.');
                return;
            }
            fetch('/api/users/register', {
                method: 'POST',
                body: new URLSearchParams(formData)
            })
            .then(response => response.text())
            .then(data => {
                if (data.includes('login')) {
                    window.location.href = '/login';
                } else {
                    document.querySelector('.profile').innerHTML += `<p style="color: red;">${data}</p>`;
                }
            })
            .catch(error => alert('Error: ' + error));
        });
    }
});

function changePage(direction) {
    alert(`Page changed by ${direction}`);
}