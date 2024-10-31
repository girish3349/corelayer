@TestGooglePage
Feature: Bing Page Test
  Validating the Bing page

#  Background:
#    Given Read workbook "BingTest" and sheet named "Bing"

  Scenario Outline: TS_01
    Given Navigate to Bing Page
    When Enter a search "search" term and click search button

    Examples:
      | search |
      | Search |
