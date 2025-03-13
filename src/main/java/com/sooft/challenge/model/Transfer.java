package com.sooft.challenge.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.Join;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.jpa.domain.Specification;

@Getter
@Setter
@Entity
@SQLRestriction("deleted_at IS NULL")
@SQLDelete(sql = "UPDATE challenge.transfer SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Table(schema = "challenge", name = "transfer")
@ToString(callSuper = true)
public class Transfer extends AbstractTrackeable {

  private static final String CREDIT_ACCOUNT = "creditAccount";
  private static final String DEBIT_ACCOUNT = "debitAccount";

  @Id
  private UUID id;
  private Double amount;
  @ManyToOne( fetch = FetchType.LAZY)
  @JoinColumn(name = "company_id", referencedColumnName = "id")
  private Company company;
  private String debitAccount;
  private String creditAccount;

  public static Specification<Transfer> idsIn(List<UUID> value) {
    return (root, query, builder) -> builder.in(root.get("id")).value(value);
  }

  public static Specification<Transfer> companyIdsIn(List<UUID> value) {
    return (root, query, criteriaBuilder) -> {
      Join<Transfer, Company> transferCompany = root.join(
          "company");
      return criteriaBuilder.in(transferCompany.get("id")).value(value);
    };
  }

  public static Specification<Transfer> createdAtGte(LocalDateTime createdAt) {
    return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("createdAt"), createdAt);
  }

  public static Specification<Transfer> createdAtLt(LocalDateTime createdAt) {
    return (root, query, builder) -> builder.lessThan(root.get("createdAt"), createdAt);
  }

  public static Specification<Transfer> debitAccountEquals(String debitAccount) {
    return (root, query, builder) -> builder.equal(root.get(DEBIT_ACCOUNT), debitAccount);
  }

  public static Specification<Transfer> creditAccountEquals(String creditAccount) {
    return (root, query, builder) -> builder.equal(root.get(CREDIT_ACCOUNT), creditAccount);
  }

  public static Specification<Transfer> isDebitAccount(boolean isDebitAccount) {
    if(isDebitAccount) {
      return (root, query, builder) -> builder.isNotNull(root.get(DEBIT_ACCOUNT));
    } else {
      return (root, query, builder) -> builder.isNull(root.get(DEBIT_ACCOUNT));
    }
  }

  public static Specification<Transfer> isCreditAccount(boolean isCreditAccount) {
    if(isCreditAccount) {
      return (root, query, builder) -> builder.isNotNull(root.get(CREDIT_ACCOUNT));
    } else {
      return (root, query, builder) -> builder.isNull(root.get(CREDIT_ACCOUNT));
    }
  }

}