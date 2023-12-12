package com.VotingApplication.Controller;


import com.VotingApplication.Entity.Candidate;
import com.VotingApplication.Service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @PostMapping("/entercandidate")
    public ResponseEntity<String> enterCandidate(@RequestParam String name) {
        if (candidateService != null) {
            System.out.println(name);
            candidateService.enterCandidate(name);
            return new ResponseEntity<>("Candidate Added", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Candidate Already Added", HttpStatus.ALREADY_REPORTED);
        }
    }


    @PostMapping("/castvote")
    public ResponseEntity<Integer> castVote(@RequestParam String name) {
        int voteCount = candidateService.castVote(name);
        if (voteCount == -1) {
            return ResponseEntity.badRequest().body(-1); // Invalid candidate
        }
        return ResponseEntity.ok(voteCount);
    }

    @GetMapping("/countvote")
    public ResponseEntity<Integer> countVote(@RequestParam String name) {
        if (candidateService != null) {
            int voteCount = candidateService.countVote(name);
            if (voteCount == -1) {
                return ResponseEntity.badRequest().body(-1); // Invalid candidate
            }
            return ResponseEntity.ok(voteCount);
        } else {
            return ResponseEntity.badRequest().build(); // CandidateService is null
        }
    }

    @GetMapping("/listvotes")
    public ResponseEntity<Map<String, Integer>> listVotes() {
        if (candidateService != null) {
            return ResponseEntity.ok(candidateService.listVotes());
        } else {
            return ResponseEntity.badRequest().build(); // CandidateService is null
        }
    }

    @GetMapping("/getwinner")
    public ResponseEntity<String> getWinner() {
        if (candidateService != null) {
            String winner = candidateService.getWinner();
            return ResponseEntity.ok(winner);
        } else {
            return ResponseEntity.badRequest().build(); // CandidateService is null
        }
    }
}

//    @PostMapping("/castvote")
//    public ResponseEntity<Integer> castVote(@RequestParam String name) {
//        if (candidateService != null) {
//            int voteCount = candidateService.castVote(name);
//            if (voteCount == -1) {
//                return ResponseEntity.badRequest().body(-1); // Invalid candidate
//            }
//            return ResponseEntity.ok(voteCount);
//        } else {
//            return ResponseEntity.badRequest().build(); // CandidateService is null
//        }
//    }

