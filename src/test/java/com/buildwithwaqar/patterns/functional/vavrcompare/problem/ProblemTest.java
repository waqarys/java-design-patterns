package com.buildwithwaqar.patterns.functional.vavrcompare.problem;
import org.junit.jupiter.api.Test; import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
class ProblemTest { @Test void bothNone(){ assertEquals("zazr-none", OptionCompare.zazrDescribe(null)); assertEquals("vavr-none", OptionCompare.vavrStyleDescribe(null)); } }
