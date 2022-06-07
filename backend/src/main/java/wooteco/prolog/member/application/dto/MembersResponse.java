package wooteco.prolog.member.application.dto;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import wooteco.prolog.member.domain.Member;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MembersResponse {

    private static final int ONE_INDEXED_PARAMETER = 1;

    private List<MemberResponse> data;
    private Long totalSize;
    private int totalPage;
    private int currPage;

    public static MembersResponse of(Page<Member> originData) {
        List<MemberResponse> data = originData.getContent().stream()
            .map(it -> MemberResponse.of(it))
            .collect(Collectors.toList());

        return new MembersResponse(data, originData.getTotalElements(), originData.getTotalPages(),
                                   originData.getNumber() + 1);
    }
}
