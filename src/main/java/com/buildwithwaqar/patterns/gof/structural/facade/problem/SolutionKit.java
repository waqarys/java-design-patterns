package com.buildwithwaqar.patterns.gof.structural.facade.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;

final class Inventory { boolean reserve(String sku){ return sku!=null && !sku.isBlank(); } void release(String sku){} }
final class Payments { boolean charge(int cents){ return cents>0; } }
final class Shipping { boolean ship(String addr){ return addr!=null; } }
final class OrderFacade {
  private final Inventory inv=new Inventory();
  private final Payments pay=new Payments();
  private final Shipping ship=new Shipping();
  String placeOrder(String sku, int cents, String addr){
    if (!inv.reserve(sku)) return "FAIL:inventory";
    if (!pay.charge(cents)) { inv.release(sku); return "FAIL:payment"; }
    if (!ship.ship(addr)) return "FAIL:shipping";
    return "OK:"+sku;
  }
}
