@TestYahooPage
Feature: Yahoo Page Test
  Validating the Yahoo Page

#  Background:
#    Given Read workbook "YahooTest" and sheet named "Yahoo"

  Scenario Outline: YahooUser
    Given User navigates to Yahoo page
    And verfies that the Yahoo page is loading and loaded and click Sign In button
    When Enter the <username> and clik Next button

    Examples:
      | username |
      | UserName |