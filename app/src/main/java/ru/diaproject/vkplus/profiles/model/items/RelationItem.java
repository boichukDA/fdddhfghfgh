package ru.diaproject.vkplus.profiles.model.items;


public class RelationItem extends KeyValueItem{
    private String key;
    private String relationName;
    private CaseType caseType;
    private String pretext;
    private String caseTypeForQuery;
    private Integer partnerId;
    private boolean partnerContains;
    private String nomFirstName;
    private String nomLastName;
    private String caseFirstName;
    private String caseLastName;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public CaseType getCaseType() {
        return caseType;
    }

    public void setCaseType(CaseType caseType) {
        this.caseType = caseType;
    }

    public String getPretext() {
        return pretext;
    }

    public void setPretext(String pretext) {
        this.pretext = pretext;
    }

    public String getCaseTypeForQuery() {
        return caseTypeForQuery;
    }

    public void setCaseTypeForQuery(String caseTypeForQuery) {
        this.caseTypeForQuery = caseTypeForQuery;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(Integer partnerId) {
        this.partnerId = partnerId;
    }

    public boolean isPartnerContains() {
        return partnerContains;
    }

    public void setPartnerContains(boolean partnerContains) {
        this.partnerContains = partnerContains;
    }

    public String getNomFirstName() {
        return nomFirstName;
    }

    public void setNomFirstName(String nomFirstName) {
        this.nomFirstName = nomFirstName;
    }

    public String getNomLastName() {
        return nomLastName;
    }

    public void setNomLastName(String nomLastName) {
        this.nomLastName = nomLastName;
    }

    public String getCaseLastName() {
        return caseLastName;
    }

    public void setCaseLastName(String caseLastName) {
        this.caseLastName = caseLastName;
    }

    public String getCaseFirstName() {
        return caseFirstName;
    }

    public void setCaseFirstName(String caseFirstName) {
        this.caseFirstName = caseFirstName;
    }
}
