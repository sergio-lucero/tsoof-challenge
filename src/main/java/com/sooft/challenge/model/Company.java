package com.sooft.challenge.model;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
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
@SQLDelete(sql = "UPDATE challenge.company SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Table(schema = "challenge", name = "company")
@ToString(callSuper = true)
public class Company extends AbstractTrackeable {

  public static final String CREATED_AT = "createdAt";

  @Id
  private UUID id;
  private String cuit;
  private String companyName;

  public static Specification<Company> idsIn(List<UUID> value) {
    return (root, query, builder) -> builder.in(root.get("id")).value(value);
  }

  public static Specification<Company> cuitsIn(List<String> value) {
    return (root, query, builder) -> builder.in(root.get("cuit")).value(value);
  }

  public static Specification<Company> companyNamesIn(List<String> value) {
    return (root, query, builder) -> builder.in(root.get("companyName")).value(value);
  }

  public static Specification<Company> createdAtGte(LocalDateTime createdAt) {
    return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get(CREATED_AT), createdAt);
  }

  public static Specification<Company> createdAtLt(LocalDateTime createdAt) {
    return (root, query, builder) -> builder.lessThan(root.get(CREATED_AT), createdAt);
  }

  public static Specification<Company> withTransferAfter(LocalDateTime value) {
    return (root, query, criteriaBuilder) -> {

      Subquery<Transfer> subQuery = query.subquery(Transfer.class);
      Root<Transfer> subRoot = subQuery.from(Transfer.class);
      Predicate modelPredicate = criteriaBuilder.equal(root.get("id"), subRoot.get("company").get("id"));
      Predicate datePredicate = criteriaBuilder.greaterThanOrEqualTo(subRoot.get(CREATED_AT), value);
      return criteriaBuilder.exists(subQuery.where(modelPredicate, datePredicate));
    };
  }

}