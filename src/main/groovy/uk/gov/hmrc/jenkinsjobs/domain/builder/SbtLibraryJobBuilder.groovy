package uk.gov.hmrc.jenkinsjobs.domain.builder

import javaposse.jobdsl.dsl.DslFactory
import javaposse.jobdsl.dsl.Job
import uk.gov.hmrc.jenkinsjobbuilders.domain.Builder
import uk.gov.hmrc.jenkinsjobbuilders.domain.publisher.Publisher

import static java.util.Arrays.asList
import static uk.gov.hmrc.jenkinsjobs.domain.builder.JobBuilders.jobBuilder
import static uk.gov.hmrc.jenkinsjobs.domain.publisher.Publishers.defaultHtmlReportsPublisher
import static uk.gov.hmrc.jenkinsjobs.domain.publisher.Publishers.defaultJUnitReportsPublisher
import static uk.gov.hmrc.jenkinsjobs.domain.step.Steps.sbtCleanTestPublish

final class SbtLibraryJobBuilder implements Builder<Job> {

    private final String name
    private final String repository
    private boolean withJUnitReports = true

    SbtLibraryJobBuilder(String name, String repository = "hmrc/${name}") {
        this.name = name
        this.repository = repository
    }

    SbtLibraryJobBuilder withoutJUnitReports() {
        this.withJUnitReports = false
        this
    }

    Job build(DslFactory dslFactory) {
        List<Publisher> publishers = withJUnitReports ? asList(defaultHtmlReportsPublisher(), defaultJUnitReportsPublisher()) : asList(defaultHtmlReportsPublisher())

        jobBuilder(name, repository).
                    withPublishers(publishers).
                    withSteps(sbtCleanTestPublish()).build(dslFactory)
    }
}