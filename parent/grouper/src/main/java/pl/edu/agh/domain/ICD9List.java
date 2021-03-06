package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 27.05.12
 */
public class ICD9List {
    private String listCode;
    private ICD9 icd9;
    private ICDListType listType;

    public String getListCode() {
        return listCode;
    }

    public void setListCode(String listCode) {
        this.listCode = listCode;
    }

    public ICD9 getIcd9() {
        return icd9;
    }

    public void setIcd9(ICD9 icd9) {
        this.icd9 = icd9;
    }

    public ICDListType getListType() {
        return listType;
    }

    public void setListType(ICDListType listType) {
        this.listType = listType;
    }
}
