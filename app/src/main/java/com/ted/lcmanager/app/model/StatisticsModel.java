package com.ted.lcmanager.app.model;

/**
 * Created by Ted on 2015/3/31.
 */
public class StatisticsModel {
    private int All = 0;
    private int Spe = 0;
    private int Bui = 0;
    private int Foo = 0;
    private int Fau = 0;
    private int Obj = 0;
    private int Peo = 0;
    private int Tec = 0;
    private int OTH = 0;

    public void Statistics(final StealDataModel model){
        if (null == model)return;
        All++;
        switch (model.getCategory()){
            case 1:
                Bui++;
                break;
            case 2:
                Foo++;
                break;
            case 4:
                Fau++;
                break;
            case 8:
                Peo++;
                break;
            case 16:
                Tec++;
                break;
            case 32:
                Obj++;
                break;
            case 1001:
                Spe++;
                break;
            default:
                OTH++;
                break;
        }
    }

    public int getAll() {
        return All;
    }

    public int getSpe() {
        return Spe;
    }

    public int getBui() {
        return Bui;
    }

    public int getFoo() {
        return Foo;
    }

    public int getFau() {
        return Fau;
    }

    public int getObj() {
        return Obj;
    }

    public int getPeo() {
        return Peo;
    }

    public int getTec() {
        return Tec;
    }

    public int getOTH() {
        return OTH;
    }

}
