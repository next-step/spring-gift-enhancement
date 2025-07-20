package gift.E2ETest.option;

import gift.dto.option.CreateOptionRequest;
import gift.dto.option.OptionDefaultResponse;
import gift.entity.UserRole;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.request.ParameterDescriptor;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.document;

public class OptionDeleteTest extends AbstractOptionTest {

    private static final ParameterDescriptor[] OPTION_DELETE_PATH_PARAMETERS = {
            parameterWithName("productId").description("옵션이 속한 제품 ID"),
            parameterWithName("id").description("수정할 옵션 ID")
    };

    List<OptionDefaultResponse> testOptions;

    @BeforeEach
    public void setUp(RestDocumentationContextProvider provider) {
        super.setUp(provider);
        Long productId = this.testProducts.get(UserRole.ROLE_ADMIN).id();
        this.testOptions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            CreateOptionRequest request = new CreateOptionRequest("옵션 " + (i + 1), 1000L + (i * 100));
            OptionDefaultResponse response = createOptionToProduct(request, productId,this.adminToken);
            this.testOptions.add(response);
        }
    }

    private ValidatableResponse deleteWithoutDocumentation(Long productId, Long optionId, String token) {
        String url = getRequestUrl() + "/{id}";
        return RestAssured.given()
                .contentType("application/json")
                .header(AUTH_HEADER_KEY, token)
                .when()
                .delete(url, productId, optionId)
                .then();
    }

    private ValidatableResponse deleteAllWithoutDocumentation(Long productId, String token) {
        String url = getRequestUrl();
        return RestAssured.given()
                .contentType("application/json")
                .header(AUTH_HEADER_KEY, token)
                .when()
                .delete(url, productId)
                .then();
    }

    @Test
    @DisplayName("옵션 단건 삭제 성공 테스트")
    public void Option_Delete_Success() {
        Long productId = this.testProducts.get(UserRole.ROLE_ADMIN).id();
        Long optionId = this.testOptions.getFirst().id(); // 첫 번째 옵션 ID 사용
        String url = getRequestUrl() + "/{id}";

        RestAssured.given(this.spec)
                .filter(document("옵션 단건 삭제 성공",
                        pathParameters(OPTION_DELETE_PATH_PARAMETERS),
                        requestHeaders(AUTHENTICATE_HEADERS)
                    ))
                .contentType("application/json")
                .header(AUTH_HEADER_KEY, this.adminToken) // 관리자 권한으로 요청
                .when()
                .delete(url, productId, optionId)
                .then()
                .statusCode(204); // No Content 응답 확인

        // 삭제 후 옵션이 존재하지 않는지 확인
        RestAssured.given()
                .contentType("application/json")
                .header(AUTH_HEADER_KEY, this.adminToken) // 관리자 권한으로 요청
                .when()
                .get(url, productId, optionId)
                .then()
                .statusCode(404); // 옵션이 존재하는지 확인
    }

    @Test
    @DisplayName("옵션 단건 삭제 실패 테스트 - 권한 없는 사용자(403 Forbidden)")
    public void Option_Delete_Failure_NoPermission() {
        Long productId = this.testProducts.get(UserRole.ROLE_ADMIN).id();
        Long optionId = this.testOptions.getFirst().id(); // 첫 번째 옵션 ID 사용
        deleteWithoutDocumentation(productId, optionId, this.testUserTokens.get(UserRole.ROLE_USER))
                .statusCode(403); // Forbidden 응답 확인
    }

    @Test
    @DisplayName("옵션 단건 삭제 실패 테스트 - 존재하지 않는 옵션 ID(404 Not Found)")
    public void Option_Delete_Failure_NotFound() {
        Long productId = this.testProducts.get(UserRole.ROLE_ADMIN).id();
        Long nonExistentOptionId = 999L; // 존재하지 않는 옵션 ID

        deleteWithoutDocumentation(productId, nonExistentOptionId, this.adminToken)
                .statusCode(404); // Not Found 응답 확인
    }

    @Test
    @DisplayName("옵션 전체 삭제 성공 테스트")
    public void Option_Delete_All_Success() {
        Long productId = this.testProducts.get(UserRole.ROLE_ADMIN).id();

        RestAssured.given(this.spec)
                .filter(document("옵션 전체 삭제 성공",
                        pathParameters(parameterWithName("productId").description("옵션이 속한 제품 ID")),
                        requestHeaders(AUTHENTICATE_HEADERS)
                ))
                .contentType("application/json")
                .header(AUTH_HEADER_KEY, this.adminToken) // 관리자 권한으로 요청
                .when()
                .delete(getRequestUrl(), productId)
                .then()
                .statusCode(204); // No Content 응답 확인

        // 삭제 후 옵션이 존재하지 않는지 확인
        RestAssured.given()
                .contentType("application/json")
                .header(AUTH_HEADER_KEY, this.adminToken) // 관리자 권한으로 요청
                .when()
                .get(getRequestUrl(), productId)
                .then()
                .statusCode(200)
                .body("contents.size()", equalTo(0)); // 옵션이 존재하지 않음을 확인
    }

    @Test
    @DisplayName("옵션 전체 삭제 실패 테스트 - 권한 없는 사용자(403 Forbidden)")
    public void Option_Delete_All_Failure_NoPermission() {
        Long productId = this.testProducts.get(UserRole.ROLE_ADMIN).id();
        deleteAllWithoutDocumentation(productId, this.testUserTokens.get(UserRole.ROLE_USER))
                .statusCode(403); // Forbidden 응답 확인
    }
}
