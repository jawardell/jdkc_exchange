# S2 Test Plan Document

__TOC:__

1. [Regression Testing](#regression-testing)
2. [Unit Testing](#unit-testing)
3. [Integration Testing](#regression-testing)
4. [Omissions](#what-not-to-test)

__See:__ [S1 Testing Document](https://github.com/vsu-se/team2_fall18/blob/master/docs/integrationTestPlan.md)

****

### Regression Testing

> test for possible regressions from added features

***1. Regression Unit Testing***

- [ ] S1 Unit Tests are all passing
- [ ]  S2 Unit Tests are all passing
- [ ]  S2 Units did not cause regressions
- [ ]  S2 Units function as expected


***2. Regression Integration Testing***

 - [ ]  S1 Integration Tests are all passing
 - [ ]  S2 Integration Tests are all passing
 - [ ]  S1 - S2 Integration Tests are all passing
 - [ ]  New features integrate as expected
 - [ ]  New features did not cause regressions
 
****


### Unit Testing

> test functionality of new units


- [ ]  All new methods work as expected
- [ ]  All new constructors work as expected
- [ ]  All new inner classes work as expected
- [ ]  No additional objects instantiated during unit tests


****


### Integration Testing
> test the associations among new units

- [ ]  S2 associations are correct
- [ ]  S2 multiplicities are correct
- [ ]  S2 integrates with S1 correctly
- [ ]  S1 integrates with S2 correctly

****


#### What NOT to test
***Non-Functional Tests***

We did not test any of the following:

- [ ]  Time/Space Efficiency
- [ ]  Precision
- [ ]  Chronology of events
- [ ]  Exception Handling

We will test these in our extra time and if we get an assignment to do so.