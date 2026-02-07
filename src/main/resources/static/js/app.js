document.addEventListener('DOMContentLoaded', function() {
    // Sidebar toggle
    const sidebarToggle = document.getElementById('sidebarCollapse');
    const sidebar = document.getElementById('sidebar');

    if (sidebarToggle && sidebar) {
        sidebarToggle.addEventListener('click', function() {
            sidebar.classList.toggle('active');
        });
    }

    // Auto-dismiss alerts after 5 seconds
    const alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });

    // Confirm delete actions
    const deleteForms = document.querySelectorAll('form[data-confirm]');
    deleteForms.forEach(function(form) {
        form.addEventListener('submit', function(e) {
            const message = form.dataset.confirm || 'Are you sure you want to delete this item?';
            if (!confirm(message)) {
                e.preventDefault();
            }
        });
    });

    // Initialize tooltips
    const tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
    tooltipTriggerList.map(function(tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });

    // Copy coordinates to clipboard
    const copyButtons = document.querySelectorAll('.copy-coordinates');
    copyButtons.forEach(function(button) {
        button.addEventListener('click', function() {
            const lat = this.dataset.lat;
            const lng = this.dataset.lng;
            const text = lat + ', ' + lng;
            navigator.clipboard.writeText(text).then(function() {
                button.innerHTML = '<i class="bi bi-check"></i>';
                setTimeout(function() {
                    button.innerHTML = '<i class="bi bi-clipboard"></i>';
                }, 2000);
            });
        });
    });

    // Dynamic form fields
    const vehicleTypeSelect = document.getElementById('vehicleType');
    const capacityFields = document.getElementById('capacityFields');

    if (vehicleTypeSelect && capacityFields) {
        vehicleTypeSelect.addEventListener('change', function() {
            const type = this.value;
            if (type === 'bicycle') {
                document.getElementById('maxCapacityKg').value = '20';
                document.getElementById('maxPackages').value = '5';
            } else if (type === 'motorcycle') {
                document.getElementById('maxCapacityKg').value = '50';
                document.getElementById('maxPackages').value = '10';
            } else if (type === 'van') {
                document.getElementById('maxCapacityKg').value = '500';
                document.getElementById('maxPackages').value = '50';
            } else if (type === 'truck') {
                document.getElementById('maxCapacityKg').value = '2000';
                document.getElementById('maxPackages').value = '200';
            }
        });
    }
});

// Utility functions
function formatDate(dateString) {
    const options = { year: 'numeric', month: 'short', day: 'numeric' };
    return new Date(dateString).toLocaleDateString(undefined, options);
}

function formatTime(timeString) {
    const options = { hour: '2-digit', minute: '2-digit' };
    return new Date('1970-01-01T' + timeString).toLocaleTimeString(undefined, options);
}

function getStatusClass(status) {
    const statusClasses = {
        'pending': 'status-pending',
        'assigned': 'status-assigned',
        'in_transit': 'status-in_transit',
        'in_progress': 'status-in-progress',
        'delivered': 'status-delivered',
        'completed': 'status-completed',
        'failed': 'status-failed',
        'cancelled': 'status-cancelled'
    };
    return statusClasses[status] || 'bg-secondary';
}
