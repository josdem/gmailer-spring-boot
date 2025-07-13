gmailer-spring-boot
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
[![All Contributors](https://img.shields.io/badge/all_contributors-2-orange.svg?style=flat-square)](#contributors-)
<!-- ALL-CONTRIBUTORS-BADGE:END -->
----------------------------------------------
Simple Gmail API server

#### Go here for more information

https://gmailer.josdem.io/

#### To Run, Call and Configuration

* https://github.com/josdem/emailer-spring-boot/wiki

**References**
[gmail-api-client](https://github.com/josdem/gmail-api-client)

## Test Coverage Setup

This project uses JaCoCo for test coverage reporting and is configured for SonarCloud integration.

### Running Tests with Coverage

```bash
# Run tests and generate coverage report
./gradlew test jacocoTestReport

# View coverage report
open build/jacocoHtml/index.html
```

### SonarCloud Integration

The project is configured to work with SonarCloud for continuous code quality monitoring:

1. **Coverage Reports**: JaCoCo XML reports are automatically generated and configured for SonarCloud
2. **Quality Gates**: Coverage thresholds are set to ensure code quality
3. **Configuration**: See `sonar-project.properties` for detailed configuration

### Coverage Configuration

- **JaCoCo Version**: 0.8.13
- **Report Formats**: HTML and XML (required for SonarCloud)
- **Coverage Threshold**: Currently set to 0% minimum (can be adjusted as needed)
- **Exclusions**: Model classes and configuration classes are excluded from coverage requirements

### Environment Setup

Make sure you have Java 21 installed and JAVA_HOME properly set:

```bash
export JAVA_HOME=/usr/lib/jvm/java-21-openjdk-amd64
```



## Contributors ✨

Thanks goes to these wonderful people ([emoji key](https://allcontributors.org/docs/en/emoji-key)):

<!-- ALL-CONTRIBUTORS-LIST:START - Do not remove or modify this section -->
<!-- prettier-ignore-start -->
<!-- markdownlint-disable -->
<table>
  <tbody>
    <tr>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/TimothyMwangi101"><img src="https://avatars.githubusercontent.com/u/134459817?v=4?s=100" width="100px;" alt="Tim"/><br /><sub><b>Tim</b></sub></a><br /><a href="https://github.com/josdem/gmailer-spring-boot/commits?author=TimothyMwangi101" title="Code">💻</a> <a href="#infra-TimothyMwangi101" title="Infrastructure (Hosting, Build-Tools, etc)">🚇</a></td>
      <td align="center" valign="top" width="14.28%"><a href="https://github.com/Nkwenti-Severian-Ndongtsop"><img src="https://avatars.githubusercontent.com/u/180976800?v=4?s=100" width="100px;" alt="@Nkwenti @Severian"/><br /><sub><b>@Nkwenti @Severian</b></sub></a><br /><a href="https://github.com/josdem/gmailer-spring-boot/commits?author=Nkwenti-Severian-Ndongtsop" title="Code">💻</a></td>
    </tr>
  </tbody>
</table>

<!-- markdownlint-restore -->
<!-- prettier-ignore-end -->

<!-- ALL-CONTRIBUTORS-LIST:END -->

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!