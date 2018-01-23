# Created by ${author} on ${date}.

# Name of the implementation class of the report creator
# Currently supported classes are:
#   1. com.athaydes.spockframework.report.internal.HtmlReportCreator
#   2. com.athaydes.spockframework.report.template.TemplateReportCreator
com.athaydes.spockframework.report.IReportCreator=com.athaydes.spockframework.report.internal.HtmlReportCreator

# Set properties of the report creator
# For the HtmlReportCreator, the only properties available are
# (the location of the css files is relative to the classpath):
com.athaydes.spockframework.report.internal.HtmlReportCreator.featureReportCss=spock-feature-report.css
com.athaydes.spockframework.report.internal.HtmlReportCreator.summaryReportCss=spock-summary-report.css
com.athaydes.spockframework.report.internal.HtmlReportCreator.printThrowableStackTrace=false
com.athaydes.spockframework.report.internal.HtmlReportCreator.inlineCss=true
com.athaydes.spockframework.report.internal.HtmlReportCreator.enabled=true

# exclude Specs Table of Contents
com.athaydes.spockframework.report.internal.HtmlReportCreator.excludeToc=false

# Output directory (where the spock reports will be created) - relative to working directory
com.athaydes.spockframework.report.outputDir=build/spock-reports

# If set to true, hides blocks which do not have any description
com.athaydes.spockframework.report.hideEmptyBlocks=false

# Set the name of the project under test so it can be displayed in the report
com.athaydes.spockframework.report.projectName=

# Set the version of the project under test so it can be displayed in the report
com.athaydes.spockframework.report.projectVersion=Unknown

# Show the source code for each block
com.athaydes.spockframework.report.showCodeBlocks=true

# Set the root location of the Spock test source code (only used if showCodeBlocks is 'true')
com.athaydes.spockframework.report.testSourceRoots=src/test/groovy

# Set properties specific to the TemplateReportCreator
com.athaydes.spockframework.report.template.TemplateReportCreator.specTemplateFile=/templateReportCreator/spec-template.md
com.athaydes.spockframework.report.template.TemplateReportCreator.reportFileExtension=md
com.athaydes.spockframework.report.template.TemplateReportCreator.summaryTemplateFile=/templateReportCreator/summary-template.md
com.athaydes.spockframework.report.template.TemplateReportCreator.summaryFileName=summary.md
com.athaydes.spockframework.report.template.TemplateReportCreator.enabled=true