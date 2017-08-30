# RingLabs_Test

Create pilot test framework for testing NASA's open API (https://api.nasa.gov/index.html#getting-started). The purpose of the test is to check images which are made by Curiosity.
Test scenario:
1. Get first 10 Mars photos made by "Curiosity" on 1000 sol.
2. Get first 10 Mars photos made by "Curiosity" on earth date that is equals 1000 Mars sol.
3. Compare images and metadata from API. Test fails in case if any difference.

Please use standard technologies, tools and frameworks which you are usually use in test frameworks creation.

As a plus:
Using NASA’s API determine how many pictures were made by each camera (by Curiosity on 1000 sol.). 
If any camera made 10 times less images than any other - test fails. 
