<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- Modal za detaljan pregled prakse -->
<div class="modal fade" id="internshipDetailsModal" tabindex="-1" aria-labelledby="internshipDetailsModalLabel" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-lg">
        <div class="modal-content rounded-4 border-0 shadow">
            <div class="modal-header border-bottom-0 pb-0 pt-4 px-4">
                <h5 class="modal-title fw-bold text-dark" id="modalDetailsName">Internship Details</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            
            <div class="modal-body px-4 py-3">
                <div class="row g-3">
                    <div class="col-md-6">
                        <label class="form-label text-muted small fw-semibold">Company</label>
                        <div class="fw-semibold text-dark" id="modalDetailsCompany"></div>
                    </div>
                    <div class="col-md-6">
                        <label class="form-label text-muted small fw-semibold">Duration</label>
                        <div class="text-dark">
                            <span id="modalDetailsStart"></span> &mdash; <span id="modalDetailsEnd"></span>
                        </div>
                    </div>
                    <div class="col-12">
                        <label class="form-label text-muted small fw-semibold">Description</label>
                        <div class="text-dark p-3 rounded-3 bg-light" id="modalDetailsDescription"></div>
                    </div>
                    <div class="col-12">
                        <label class="form-label text-muted small fw-semibold">Terms &amp; Conditions</label>
                        <div class="text-dark p-3 rounded-3 bg-light" id="modalDetailsTerms"></div>
                    </div>
                    <div class="col-12">
                        <label class="form-label text-muted small fw-semibold">Technologies</label>
                        <div id="modalDetailsTechs" class="d-flex flex-wrap gap-1 mt-1"></div>
                    </div>
                </div>
            </div>
            
            <div class="modal-footer border-top-0 pt-0 pb-4 px-4">
                <button type="button" class="btn btn-modal btn-modal-primary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div>


<script>
document.addEventListener('DOMContentLoaded', function () {
    const modalEl = document.getElementById('internshipDetailsModal');
    if (!modalEl) return;

    // Paleta pastelnih boja (pozadina, boja teksta, obrub)
    const colorStyles = [
        { bg: '#e6f7f5', color: '#0d9488', border: '#b2f5ea' },
        { bg: '#ebf5ff', color: '#2563eb', border: '#bfdbfe' },
        { bg: '#f3e8ff', color: '#9333ea', border: '#e9d5ff' },
        { bg: '#fffbeb', color: '#d97706', border: '#fef3c7' },
        { bg: '#ffe4e6', color: '#e11d48', border: '#fecdd3' },
        { bg: '#ecfdf5', color: '#059669', border: '#a7f3d0' }
    ];

    modalEl.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        if (!button) return;

        document.getElementById('modalDetailsName').textContent = button.getAttribute('data-name') || '';
        document.getElementById('modalDetailsCompany').textContent = button.getAttribute('data-company') || '-';
        document.getElementById('modalDetailsStart').textContent = button.getAttribute('data-start') || '';
        document.getElementById('modalDetailsEnd').textContent = button.getAttribute('data-end') || '';
        document.getElementById('modalDetailsDescription').textContent = button.getAttribute('data-description') || '-';
        document.getElementById('modalDetailsTerms').textContent = button.getAttribute('data-terms') || '-';

        const modalTechs = document.getElementById('modalDetailsTechs');
        const techsRaw = button.getAttribute('data-technologies');
        modalTechs.innerHTML = '';

        if (techsRaw && techsRaw.trim() !== '') {
            const techs = techsRaw.split(',');
            techs.forEach((tech, index) => {
                const pill = document.createElement('span');
                const style = colorStyles[index % colorStyles.length];

                pill.textContent = tech.trim();
                
                pill.style.backgroundColor = style.bg;
                pill.style.color = style.color;
                pill.style.border = `1px solid ${style.border}`;
                pill.style.padding = '0.35em 0.75em';
                pill.style.borderRadius = '50rem';
                pill.style.fontSize = '0.78rem';
                pill.style.fontWeight = '600';
                pill.style.display = 'inline-flex';
                pill.style.alignItems = 'center';
                pill.style.margin = '2px';
                pill.style.lineHeight = '1';

                modalTechs.appendChild(pill);
            });
        } else {
            modalTechs.innerHTML = '<span class="text-muted small">-</span>';
        }
    });
});
</script>