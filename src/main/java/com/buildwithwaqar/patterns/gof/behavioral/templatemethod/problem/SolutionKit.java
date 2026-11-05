package com.buildwithwaqar.patterns.gof.behavioral.templatemethod.problem;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.function.*;

abstract class DataMiner {
  final String mine(String raw){ String opened=open(raw); List<String> rows=parse(opened); return analyze(rows); }
  abstract String open(String raw); abstract List<String> parse(String opened);
  String analyze(List<String> rows){ return "rows="+rows.size(); }
}
final class CsvMiner extends DataMiner {
  String open(String raw){ return raw; }
  List<String> parse(String opened){ return Arrays.stream(opened.split("\n")).filter(s->!s.isBlank()).toList(); }
}
final class JsonMiner extends DataMiner {
  String open(String raw){ return raw; }
  List<String> parse(String opened){ return Arrays.stream(opened.split("\n")).filter(s->s.contains(":")).toList(); }
}
