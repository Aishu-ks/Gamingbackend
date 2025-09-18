package com.asphalt.gamingsystem;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberService {
    private static final Logger log = LoggerFactory.getLogger(MemberService.class);

    @Autowired
    private MembersRepository repo;

    public Members create(Members member) {
        log.info("Creating member: {}", member.getName());
        member.setId(null); // let MongoDB assign ObjectId
        if (member.getBalance() == null) {
            member.setBalance(0.0); // default balance = 0
        }
        validate(member);

        // check unique phone
        if (repo.findByPhone(member.getPhone()).isPresent()) {
            log.error("Phone already exists: {}", member.getPhone());
            throw new BusinessException("Phone number already exists: " + member.getPhone());
        }

        return repo.save(member);
    }

    public List<Members> findAll() {
        log.info("Finding all members");
        return repo.findAll();
    }

    public Members findById(String id) {
        log.info("Finding member by id {}", id);
        Optional<Members> optionalMember = repo.findById(id);
        if (optionalMember.isEmpty()) {
            log.error("Attempted to find non-existing member id: {}", id);
            throw new ResourceNotFoundException("Member not found: " + id);
        }
        return optionalMember.get();
    }

    public Members update(String id, Members member) {
        Optional<Members> optionalMember = repo.findById(id);
        if (optionalMember.isEmpty()) {
            log.error("Attempted to update non-existing member id: {}", id);
            throw new ResourceNotFoundException("Member not found: " + id);
        }

        log.info("Updating member by id {}", id);
        Members oldMember = optionalMember.get();

        // update fields
        oldMember.setName(member.getName());
        oldMember.setBalance(member.getBalance() != null ? member.getBalance() : oldMember.getBalance());

        if (member.getPhone() != null && !member.getPhone().equals(oldMember.getPhone())) {
            // check unique phone
            if (repo.findByPhone(member.getPhone()).isPresent()) {
                log.error("Phone already exists: {}", member.getPhone());
                throw new BusinessException("Phone number already exists: " + member.getPhone());
            }
            oldMember.setPhone(member.getPhone());
        }

        validate(oldMember);
        return repo.save(oldMember);
    }

    public boolean delete(String id) {
        Optional<Members> optionalMember = repo.findById(id);
        if (optionalMember.isEmpty()) {
            log.error("Attempted to delete non-existing member id: {}", id);
            throw new ResourceNotFoundException("Member not found: " + id);
        }
        log.info("Deleting member by id {}", id);
        repo.deleteById(id);
        return true;
    }

    private void validate(Members member) {
        if (member.getName() == null || member.getName().trim().isEmpty()) {
            log.error("Name cannot be null or empty");
            throw new BusinessException("Name cannot be null or empty");
        }
        if (member.getBalance() != null && member.getBalance() < 0) {
            log.error("Balance cannot be negative");
            throw new BusinessException("Balance cannot be negative");
        }
        if (member.getPhone() == null || member.getPhone().trim().isEmpty()) {
            log.error("Phone cannot be null or empty");
            throw new BusinessException("Phone cannot be null or empty");
        }
    }
}
