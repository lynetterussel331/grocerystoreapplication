package com.titusgt.grocerystoreapplication.suite;


import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.titusgt.grocerystoreapplication.CalculateTestMultiple;
import com.titusgt.grocerystoreapplication.CalculateTestSingle;

@RunWith(Suite.class)
@SuiteClasses({CalculateTestSingle.class, CalculateTestMultiple.class})
public class DummyTestSuite {

}
