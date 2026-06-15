package com.mphasis.csp.dto.response;

public class CroDashboardResponseDTO {

    private Long croId;
    private String croName;

    private Long totalTickets;
    private Long pendingCro;
    private Long pendingManager;
    private Long resolved;
    private Long rejected;

    // ✅ Constructor
    public CroDashboardResponseDTO(Long croId,
                                   String croName,
                                   Long totalTickets,
                                   Long pendingCro,
                                   Long pendingManager,
                                   Long resolved,
                                   Long rejected) {
        this.croId = croId;
        this.croName = croName;
        this.totalTickets = totalTickets;
        this.pendingCro = pendingCro;
        this.pendingManager = pendingManager;
        this.resolved = resolved;
        this.rejected = rejected;
    }

    // ✅ Getters

    public Long getCroId() {
        return croId;
    }

    public String getCroName() {
        return croName;
    }

    public Long getTotalTickets() {
        return totalTickets;
    }

    public Long getPendingCro() {
        return pendingCro;
    }

    public Long getPendingManager() {
        return pendingManager;
    }

    public Long getResolved() {
        return resolved;
    }

    public Long getRejected() {
        return rejected;
    }

    // ✅ Setters

    public void setCroId(Long croId) {
        this.croId = croId;
    }

    public void setCroName(String croName) {
        this.croName = croName;
    }

    public void setTotalTickets(Long totalTickets) {
        this.totalTickets = totalTickets;
    }

    public void setPendingCro(Long pendingCro) {
        this.pendingCro = pendingCro;
    }

    public void setPendingManager(Long pendingManager) {
        this.pendingManager = pendingManager;
    }

    public void setResolved(Long resolved) {
        this.resolved = resolved;
    }

    public void setRejected(Long rejected) {
        this.rejected = rejected;
    }
}
