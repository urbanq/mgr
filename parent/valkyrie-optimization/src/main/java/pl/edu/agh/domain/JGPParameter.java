package pl.edu.agh.domain;

/**
 * User: mateusz
 * Date: 27.05.12
 */
public class JGPParameter {
    private ListType listType;
    private String listCode;
    private JGP jgp;
    private Algorithm algorithm;
    private HospitalLimit hospitalLimit;
    private AgeLimit ageLimit;
    private Sex sexLimit;
    private IncomeModeLimit incomeModeLimit;
    private OutcomeModeLimit outcomeModeLimit;

    private String firstICD9ListCode;
    private int firstICD9MinimalCount;
    private String secondICD9ListCode;
    private int secondICD9MinimalCount;

    private String mainICD10ListCode;
    private String firstICD10ListCode;
    private String secondICD10ListCode;

    private String negativeICD9ListCode;
    private String negativeICD10ListCode;

    public ListType getListType() {
        return listType;
    }

    public void setListType(ListType listType) {
        this.listType = listType;
    }

    public String getListCode() {
        return listCode;
    }

    public void setListCode(String listCode) {
        this.listCode = listCode;
    }

    public JGP getJgp() {
        return jgp;
    }

    public void setJgp(JGP jgp) {
        this.jgp = jgp;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public HospitalLimit getHospitalLimit() {
        return hospitalLimit;
    }

    public void setHospitalLimit(HospitalLimit hospitalLimit) {
        this.hospitalLimit = hospitalLimit;
    }

    public AgeLimit getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(AgeLimit ageLimit) {
        this.ageLimit = ageLimit;
    }

    public Sex getSexLimit() {
        return sexLimit;
    }

    public void setSexLimit(Sex sexLimit) {
        this.sexLimit = sexLimit;
    }

    public IncomeModeLimit getIncomeModeLimit() {
        return incomeModeLimit;
    }

    public void setIncomeModeLimit(IncomeModeLimit incomeModeLimit) {
        this.incomeModeLimit = incomeModeLimit;
    }

    public OutcomeModeLimit getOutcomeModeLimit() {
        return outcomeModeLimit;
    }

    public void setOutcomeModeLimit(OutcomeModeLimit outcomeModeLimit) {
        this.outcomeModeLimit = outcomeModeLimit;
    }

    public String getFirstICD9ListCode() {
        return firstICD9ListCode;
    }

    public void setFirstICD9ListCode(String firstICD9ListCode) {
        this.firstICD9ListCode = firstICD9ListCode;
    }

    public int getFirstICD9MinimalCount() {
        return firstICD9MinimalCount;
    }

    public void setFirstICD9MinimalCount(int firstICD9MinimalCount) {
        this.firstICD9MinimalCount = firstICD9MinimalCount;
    }

    public String getSecondICD9ListCode() {
        return secondICD9ListCode;
    }

    public void setSecondICD9ListCode(String secondICD9ListCode) {
        this.secondICD9ListCode = secondICD9ListCode;
    }

    public int getSecondICD9MinimalCount() {
        return secondICD9MinimalCount;
    }

    public void setSecondICD9MinimalCount(int secondICD9MinimalCount) {
        this.secondICD9MinimalCount = secondICD9MinimalCount;
    }

    public String getMainICD10ListCode() {
        return mainICD10ListCode;
    }

    public void setMainICD10ListCode(String mainICD10ListCode) {
        this.mainICD10ListCode = mainICD10ListCode;
    }

    public String getFirstICD10ListCode() {
        return firstICD10ListCode;
    }

    public void setFirstICD10ListCode(String firstICD10ListCode) {
        this.firstICD10ListCode = firstICD10ListCode;
    }

    public String getSecondICD10ListCode() {
        return secondICD10ListCode;
    }

    public void setSecondICD10ListCode(String secondICD10ListCode) {
        this.secondICD10ListCode = secondICD10ListCode;
    }

    public String getNegativeICD9ListCode() {
        return negativeICD9ListCode;
    }

    public void setNegativeICD9ListCode(String negativeICD9ListCode) {
        this.negativeICD9ListCode = negativeICD9ListCode;
    }

    public String getNegativeICD10ListCode() {
        return negativeICD10ListCode;
    }

    public void setNegativeICD10ListCode(String negativeRecognitionListCode) {
        this.negativeICD10ListCode = negativeRecognitionListCode;
    }

    public static enum ListType {
        ICD9,
        ICD10;
    }
}
