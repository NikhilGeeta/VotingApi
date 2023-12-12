package com.VotingApplication.Testing;


import com.VotingApplication.Entity.Candidate;
import com.VotingApplication.Service.CandidateService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CandidateServiceTest {

    @Autowired
    private CandidateService candidateService;

    @Test
    public void testEnterCandidate() {
        // Given
        String candidateName = "John";

        // When
        candidateService.enterCandidate(candidateName);

        // Then
        Candidate candidate = candidateService.getCandidates().get(candidateName);
        assertNotNull(candidate);
        assertEquals(candidateName, candidate.getName());
        assertEquals(0, candidate.getVoteCount());
    }

    @Test
    public void testCastVote() {
        // Given
        String candidateName = "Alice";
        candidateService.enterCandidate(candidateName);

        // When
        int voteCount = candidateService.castVote(candidateName);

        // Then
        assertEquals(1, voteCount);
    }

    @Test
    public void testCastVoteInvalidCandidate() {
        // Given
        String candidateName = "Bob";

        // When
        int voteCount = candidateService.castVote(candidateName);

        // Then
        assertEquals(-1, voteCount);
    }

    @Test
    public void testCountVote() {
        // Given
        String candidateName = "Charlie";
        candidateService.enterCandidate(candidateName);
        candidateService.castVote(candidateName);

        // When
        int voteCount = candidateService.countVote(candidateName);

        // Then
        assertEquals(1, voteCount);
    }

    @Test
    public void testCountVoteInvalidCandidate() {
        // Given
        String candidateName = "David";

        // When
        int voteCount = candidateService.countVote(candidateName);

        // Then
        assertEquals(-1, voteCount);
    }

    @Test
    public void testListVotes() {
        // Given
        String candidate1 = "Eve";
        String candidate2 = "Frank";
        candidateService.enterCandidate(candidate1);
        candidateService.enterCandidate(candidate2);
        candidateService.castVote(candidate1);

        // When
        Map<String, Integer> votes = candidateService.listVotes();

        // Then
        assertEquals(2, votes.size());
        assertTrue(votes.containsKey(candidate1));
        assertTrue(votes.containsKey(candidate2));
        assertEquals(1, (int) votes.get(candidate1));
        assertEquals(0, (int) votes.get(candidate2));
    }

    @Test
    public void testGetWinner() {
        // Given
        String candidate1 = "Grace";
        String candidate2 = "Henry";
        String candidate3 = "Ivy";
        candidateService.enterCandidate(candidate1);
        candidateService.enterCandidate(candidate2);
        candidateService.enterCandidate(candidate3);
        candidateService.castVote(candidate1);
        candidateService.castVote(candidate2);
        candidateService.castVote(candidate3);
        candidateService.castVote(candidate3);

        // When
        String winner = candidateService.getWinner();

        // Then
        assertEquals(candidate3, winner);
    }
}

