package com.buildwithwaqar.patterns.enterprise.specification.problem;

import java.util.*;

public final class NaiveFilter {
  public static List<String> filter(List<int[]> items){ // [price, stock]
    List<String> out=new ArrayList<>();
    for (int i=0;i<items.size();i++) if(items.get(i)[0]<1000 && items.get(i)[1]>0) out.add("item"+i);
    return out;
  }
}
