package ru.spbstu.icc.kspt.inspace.api;


import ru.spbstu.icc.kspt.inspace.model.research.ResearchType;

public class Research extends Upgradable{
    private final ru.spbstu.icc.kspt.inspace.model.research.Research research;

     Research(ru.spbstu.icc.kspt.inspace.model.research.Research research) {
        super(research);
        this.research = research;
    }

    public ResearchType getType(){
        return research.getType();
    }
}
