package com.senials.partyboards.repository;

import com.senials.entity.PartyBoard;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class PartyBoardSpecification {

    /*
        SELECT * FROM PARTY_BOARD
        WHERE
            :sortColumn </> (SELECT :sortColumn FROM PARTY_BOARD WHERE party_board_number = :cursor)
            OR
            :sortColumn = (SELECT :sortColumn FROM PARTY_BOARD WHERE party_board_number = :cursor)
            AND
            party_board_number </> :cursor
        AND
            party_board_name like %workshop%
        ORDER BY party_board_open_date asc/desc, party_board_number asc/desc
        LIMIT :size

        정렬, 행 제한은 pageable로 수행
    */

    /* LocalDate형으로 정렬할 때 */
    public static Specification<PartyBoard> searchLoadLocalDate (String sortColumn, String keyword, Integer cursor, boolean isAscending) {
        return (root, query, criteriaBuilder) -> {

            /* 서브쿼리 세팅 */
            // 서브쿼리 생성 및 반환 컬럼 자료형 지정
            Subquery<LocalDate> subquery = query.subquery(LocalDate.class);

            // 서브쿼리 FROM절 - FROM PartyBoard 엔티티
            Root<PartyBoard> subqueryRoot = subquery.from(PartyBoard.class);

            // SELECT :sortColumn FROM PARTY_BOARD WHERE party_board_number = cursor
            subquery.select(subqueryRoot.get(sortColumn))
                    .where(criteriaBuilder.equal(subqueryRoot.get("partyBoardNumber"), cursor));


            // 조건 1: party_board_open_date </> (서브쿼리 결과)
            Predicate condition1 = null;
            if (isAscending) {
                condition1 = criteriaBuilder.greaterThan(root.get(sortColumn), subquery);
            } else {
                condition1 = criteriaBuilder.lessThan(root.get(sortColumn), subquery);
            }


            // 조건 2: party_board_open_date = (서브쿼리 결과) AND party_board_number </> cursor
            Predicate condition2 = null;
            Predicate temp1 = criteriaBuilder.equal(root.get(sortColumn), subquery);
            Predicate temp2 = null;
            if(isAscending) {
                temp2 = criteriaBuilder.greaterThan(root.get("partyBoardNumber"), cursor);
            } else {
                temp2 = criteriaBuilder.lessThan(root.get("partyBoardNumber"), cursor);
            }
            condition2 = criteriaBuilder.and(temp1, temp2);


            /* 검색어 있을 때 */
            // 조건 3: party_board_name LIKE '%workshop%'
            if (keyword != null && !keyword.isBlank()) {
                Predicate condition3 = criteriaBuilder.like(root.get("partyBoardName"), "%" + keyword + "%");

                // 최종 조건 조합: (조건 1 OR 조건 2) AND 조건 3
                return criteriaBuilder.and(
                        criteriaBuilder.or(condition1, condition2),
                        condition3
                );
            }else {
                // 최종 조건 조합: 조건 1 OR 조건 2
                return criteriaBuilder.or(condition1, condition2);
            }
        };
    }

    /* Integer형으로 정렬할 때 */
    public static Specification<PartyBoard> searchLoadInteger (String sortColumn, String keyword, Integer cursor, boolean isAscending) {
        return (root, query, criteriaBuilder) -> {

            /* 서브쿼리 세팅 */
            // 서브쿼리 생성 및 반환 컬럼 자료형 지정
            Subquery<Integer> subquery = query.subquery(Integer.class);

            // 서브쿼리 FROM절 - FROM PartyBoard 엔티티
            Root<PartyBoard> subqueryRoot = subquery.from(PartyBoard.class);

            // SELECT :sortColumn FROM PARTY_BOARD WHERE party_board_number = cursor
            subquery.select(subqueryRoot.get(sortColumn))
                    .where(criteriaBuilder.equal(subqueryRoot.get("partyBoardNumber"), cursor));


            // 조건 1: party_board_open_date </> (서브쿼리 결과)
            Predicate condition1 = null;
            if (isAscending) {
                condition1 = criteriaBuilder.greaterThan(root.get(sortColumn), subquery);
            } else {
                condition1 = criteriaBuilder.lessThan(root.get(sortColumn), subquery);
            }


            // 조건 2: party_board_open_date = (서브쿼리 결과) AND party_board_number </> cursor
            Predicate condition2 = null;
            Predicate temp1 = criteriaBuilder.equal(root.get(sortColumn), subquery);
            Predicate temp2 = null;
            if(isAscending) {
                temp2 = criteriaBuilder.greaterThan(root.get("partyBoardNumber"), cursor);
            } else {
                temp2 = criteriaBuilder.lessThan(root.get("partyBoardNumber"), cursor);
            }
            condition2 = criteriaBuilder.and(temp1, temp2);


            /* 검색어 있을 때 */
            // 조건 3: party_board_name LIKE '%workshop%'
            if (keyword != null && !keyword.isBlank()) {
                Predicate condition3 = criteriaBuilder.like(root.get("partyBoardName"), "%" + keyword + "%");

                // 최종 조건 조합: (조건 1 OR 조건 2) AND 조건 3
                return criteriaBuilder.and(
                        criteriaBuilder.or(condition1, condition2),
                        condition3
                );
            }else {
                // 최종 조건 조합: 조건 1 OR 조건 2
                return criteriaBuilder.or(condition1, condition2);
            }
        };
    }
}
