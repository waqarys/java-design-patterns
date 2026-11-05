package com.buildwithwaqar.patterns.gof.creational.prototype.problem;

import java.util.*;

import java.util.*;
final class MeetingTemplate implements Cloneable {
  private String title; private List<String> attendees;
  public MeetingTemplate(String title, List<String> attendees){
    this.title=title; this.attendees=new ArrayList<>(attendees);
  }
  public MeetingTemplate copy(){
    return new MeetingTemplate(title + " (copy)", new ArrayList<>(attendees));
  }
  public MeetingTemplate withTitle(String t){ this.title=t; return this; }
  public List<String> attendees(){ return List.copyOf(attendees); }
  public String title(){ return title; }
  public String describe(){ return title+":"+attendees; }
}
