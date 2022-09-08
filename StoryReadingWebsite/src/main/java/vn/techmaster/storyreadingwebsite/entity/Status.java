package vn.techmaster.storyreadingwebsite.entity;

public enum Status {
    HOANTHANH("Full") ,
    DANGTIENHANH("Đang ra") ;
    public final String label;
    private Status(String label) {
        this.label = label;
    }
}
