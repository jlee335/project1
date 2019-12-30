package com.example.project1.MLthings;

public class ML_Image_Object {
    private int imResource; // 이미지 형태에 따라 바뀔 수 있음.
    private String Label;
    private boolean labelled;

    public int getImResource(){return imResource;}
    public String getLabel(){return Label;}
    public boolean isLabelled(){return labelled;}

    public void setImResoruce(int nres){
        this.imResource = nres;
    }
    public void setLabel(String label){
        this.Label = label;
    }
    public void setLabelled(boolean status){
        this.labelled = status;
    }

    public ML_Image_Object(int imResource,String Label, boolean labelled){
        this.imResource = imResource;
        this.Label = Label;
        this.labelled = labelled;
    }

}
