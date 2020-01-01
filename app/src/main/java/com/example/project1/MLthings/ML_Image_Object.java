package com.example.project1.MLthings;

public class ML_Image_Object {
    private int imResource; // 이미지 형태에 따라 바뀔 수 있음.\\

    // 여기는 path 를 이용하고, 이것들을 보여줄 때 Bitmap 형태를 띌 것이다.
    private String path;
    private String imgID;

    //ML_related functions
    private String Label;
    private boolean labelled;

    public String getPath(){return path;}
    public String getImID(){return imgID;}
    public int getImResource(){return imResource;}
    public String getLabel(){return Label;}
    public boolean isLabelled(){return labelled;}


    public void setPath(String path){this.path = path;}
    public void setImID(String imgID){this.imgID = imgID;}
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
