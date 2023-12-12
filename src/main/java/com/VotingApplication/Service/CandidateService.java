package com.VotingApplication.Service;

import com.VotingApplication.Entity.Candidate;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class CandidateService {
    private Map<String, Candidate> candidates = new ConcurrentHashMap<>();

    public synchronized void enterCandidate(String name) {
        Candidate candidate = new Candidate(name, 0);
        if (!candidates.containsKey(name) && name != null) {
            candidates.put(name, candidate);
        }
        System.out.println(getCandidates());
    }

    public int castVote(String name) {
        Candidate candidate = candidates.get(name);
        if (candidate != null) {
            candidate.setVoteCount(candidate.getVoteCount() + 1);
            return candidate.getVoteCount();
        }
        return -1; // Invalid candidate
    }

    public int countVote(String name) {
        Candidate candidate = candidates.get(name);
        return candidate != null ? candidate.getVoteCount() : -1; // Invalid candidate
    }

    public Map<String, Integer> listVotes() {
        Map<String, Integer> result = new HashMap<>();
        candidates.forEach((name, candidate) -> result.put(name, candidate.getVoteCount()));
        return result;
    }

    public String getWinner() {
        Optional<Candidate> winner = candidates.values().stream().max(Comparator.comparingInt(Candidate::getVoteCount));
        return winner.map(Candidate::getName).orElse(null);
    }
    public Map<String, Candidate> getCandidates() {
        return candidates;
    }

}

