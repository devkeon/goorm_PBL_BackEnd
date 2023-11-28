package com.goormthon.tricount;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tuple implements Comparable<Tuple>{
    private final int first;
    private final String second;

    public Tuple(int first, String second) {
        this.first = first;
        this.second = second;
    }


    @Override
    public int compareTo(Tuple o) {
        if (o.getFirst() > this.getFirst()){
            return -1;
        } else if (o.getFirst() == this.getFirst()) {
            return 0;
        }
        return 1;
    }

}
