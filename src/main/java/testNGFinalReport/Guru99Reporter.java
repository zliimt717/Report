package testNGFinalReport;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlSuite;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Guru99Reporter implements IReporter {

    @Override
    public void generateReport(List<XmlSuite> arg0, List<ISuite> arg1, String outputDirectory) {
        // Second parameter of this method ISuit weill contain all the suite executed.
        for(ISuite iSuite:arg1){
            // Get a map of result of a single suite at a time
            Map<String, ISuiteResult> results=iSuite.getResults();
            // Get the key of the result map
            Set<String> keys=results.keySet();
            // Go to each map value one by one
            for(String key: keys){
                // The context object of current result
                ITestContext context=results.get(key).getTestContext();
                // Print Suite detail in Console
                System.out.println("Suite Name ->"+context.getName()
                    +"\n::Report output Directory->"+context.getOutputDirectory()
                        +"\n::Suite Name ->"+context.getSuite().getName()
                        +"\n::Start Date Time for execution->"+context.getStartDate()
                        +"\n::End Date Time for execution->"+context.getEndDate()
                );
                //Get Map for failed test cases
                IResultMap rstfailedMap=context.getFailedTests();
                //Get method detail of failed test cases
                Collection<ITestNGMethod> failedMethods=rstfailedMap.getAllMethods();
                //Loop one by one in all failed methods
                System.out.println("----------FAILED TEST CASE----------");
                for(ITestNGMethod iTestNGMethod:failedMethods){
                    // Print failed test cases detail
                    System.out.println("TESTCASE NAME->"+iTestNGMethod.getMethodName()
                            +"\n Description->"+iTestNGMethod.getDescription()
                            +"\nPriority->"+iTestNGMethod.getPriority()
                            +"\nDate->"+new Date(iTestNGMethod.getDate())
                    );
                }

                //Get Map for failed test cases
                IResultMap restpassedMap=context.getPassedTests();
                //Get method detail of failed test cases
                Collection<ITestNGMethod> passedMethods=restpassedMap.getAllMethods();
                //Loop one by one in all failed methods
                System.out.println("----------PASSED TEST CASE----------");
                for(ITestNGMethod iTestNGMethod:passedMethods){
                    // Print failed test cases detail
                    System.out.println("TESTCASE NAME->"+iTestNGMethod.getMethodName()
                            +"\n Description->"+iTestNGMethod.getDescription()
                            +"\nPriority->"+iTestNGMethod.getPriority()
                            +"\nDate->"+new Date(iTestNGMethod.getDate())
                    );
                }
            }
        }
    }

}
