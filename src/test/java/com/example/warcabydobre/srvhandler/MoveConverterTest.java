package com.example.warcabydobre.srvhandler;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ TestGetCommandType.class, TestMoveToStringConversion.class, TestStringToMoveConversion.class })
public class MoveConverterTest {

}
