package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 20.05.12
 */
public class ICD10Wrapper implements Comparable<ICD10Wrapper> {
    private ICD10 icd10;

    public ICD10 getIcd10() {
        return icd10;
    }

    public void setIcd10(ICD10 icd10) {
        this.icd10 = icd10;
    }

    @Override
    public int compareTo(ICD10Wrapper o) {
        return icd10.compareTo(o.icd10);
    }
}
