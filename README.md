# Software Verification and Validation (VVS) Projects

This repository contains two projects developed for the Software Verification and Validation course at FCUL during the 2nd Semester of 2024-2025 academic year.

**Author:** Gabriel Henriques (fc58182)

**Final Grade:** 18/20

## Project 1: Trie Data Structure Unit Testing

Located in `/vvs_unit_testing/`, this project focused on comprehensive unit testing of a Trie data structure implementation using ternary trees. The testing approach included:

- Code Coverage testing (line and branch coverage)
- Path-based testing (Edge-Pair and Prime Path Coverage)
- Data flow testing (All-Du-Paths and All-Coupling-Use-Paths Coverage)
- Logic-based testing
- Input State Partitioning using Base Choice Coverage
- Property-based testing using JUnit QuickCheck
- Mutation testing using PIT

For detailed information, see [trie_testing_report.md](vvs_unit_testing/trie_testing_report.md).

## Project 2: WebApp Integration Testing

Located in `/vvs_integration_testing/`, this project implemented comprehensive integration testing for a web application. The testing strategy employed three complementary approaches:

- HtmlUnit for end-to-end testing of complete user workflows
- DBSetup for validating database operations and data integrity
- Mockito for testing service layer interactions in isolation

For detailed information, see [webapp_testing_report.md](vvs_integration_testing/webapp_testing_report.md).