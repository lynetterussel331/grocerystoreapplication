package com.titusgt.grocerystoreapplication.suite;


import com.titusgt.grocerystoreapplication.CalculateTestMultiple;
import com.titusgt.grocerystoreapplication.CalculateTestSingle;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
//@SuiteClasses({CalculateTestSingle.class, CalculateTestMultiple.class})
@SuiteClasses({CalculateTestSingle.class})
public class DummyTestSuite {

}
