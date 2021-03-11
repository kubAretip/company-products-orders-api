package pl.kubaretip.cpo.api.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.kubaretip.cpo.api.JWTUtilTestConfig;
import pl.kubaretip.cpo.api.SpringSecurityWebTestConfig;
import pl.kubaretip.cpo.api.TranslatorTestConfig;
import pl.kubaretip.cpo.api.domain.Category;
import pl.kubaretip.cpo.api.dto.CategoryDTO;
import pl.kubaretip.cpo.api.dto.mapper.CategoryMapper;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.service.CategoryService;
import pl.kubaretip.cpo.api.web.rest.request.EditCategoryRequest;
import pl.kubaretip.cpo.api.web.rest.request.NewCategoryRequest;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static pl.kubaretip.cpo.api.SpringSecurityWebTestConfig.MODERATOR_USER;

@WebMvcTest(controllers = CategoryController.class)
@ContextConfiguration(classes = {SpringSecurityWebTestConfig.class, JWTUtilTestConfig.class, TranslatorTestConfig.class})
public class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private CategoryMapper categoryMapper;

    @WithMockUser
    @Test
    public void shouldReturnCategoriesDTOListWhenGetAllCategories() throws Exception {

        // given
        given(categoryService.getAllCategories()).willReturn(Collections.emptyList());
        given(categoryMapper.mapToDTOList(anyList())).willReturn(Arrays.asList(
                new CategoryDTO(1L, "t1"),
                new CategoryDTO(2L, "t2"),
                new CategoryDTO(3L, "t3")));


        // when
        var request = MockMvcRequestBuilders.get("/categories");

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("t1")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].name", is("t2")))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$[2].name", is("t3")));
        then(categoryService).should(times(1)).getAllCategories();
        then(categoryMapper).should(times(1)).mapToDTOList(anyList());
    }


    @WithMockUser
    @Test
    public void shouldReturnCategoryDTOWhenGetCategoryById() throws Exception {

        // given
        given(categoryService.getCategoryById(anyLong())).willReturn(new Category());
        given(categoryMapper.mapToDTO(any(Category.class)))
                .willReturn(new CategoryDTO(1L, "t1"));


        // when
        var request = MockMvcRequestBuilders.get("/categories/{id}", 1);

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("t1")));
        then(categoryService).should(times(1)).getCategoryById(1);
        then(categoryMapper).should(times(1)).mapToDTO(any(Category.class));
    }

    @WithMockUser
    @Test
    public void shouldReturn404WhenNotFoundCategoryById() throws Exception {

        // given
        given(categoryService.getCategoryById(1)).willThrow(NotFoundException.class);

        // when
        var request = MockMvcRequestBuilders.get("/categories/{id}", 1);

        // then
        mockMvc.perform(request)
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        then(categoryService).should(times(1)).getCategoryById(1);
        then(categoryMapper).should(times(0)).mapToDTO(any(Category.class));
    }


    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MODERATOR_USER)
    @Test
    public void shouldReturn200WhenCreateNewCategory() throws Exception {

        // given
        var newCategoryRequest = new NewCategoryRequest("test");
        var newCategory = new Category();
        newCategory.setId(1L);
        given(categoryMapper.mapNewCategoryRequestToCategoryDTO(any(NewCategoryRequest.class))).willReturn(new CategoryDTO());
        given(categoryService.createCategory(any(CategoryDTO.class))).willReturn(newCategory);
        given(categoryMapper.mapToDTO(any(Category.class))).willReturn(new CategoryDTO(1L, "test"));

        // when
        var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategoryRequest));

        // then
        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("name", is("test")));
        then(categoryMapper).should(times(1)).mapNewCategoryRequestToCategoryDTO(any(NewCategoryRequest.class));
        then(categoryService).should(times(1)).createCategory(any(CategoryDTO.class));
        then(categoryMapper).should(times(1)).mapToDTO(any(Category.class));
    }

    @ParameterizedTest
    @MethodSource("invalidNewCategoryRequests")
    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MODERATOR_USER)
    public void shouldReturn400WhenCreateNewCategoryWithInvalidData(NewCategoryRequest newCategoryRequest) throws Exception {

        // given (in method source)
        // when
        var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newCategoryRequest));

        // then
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
        then(categoryService).should(times(0)).createCategory(any(CategoryDTO.class));
    }

    public static Stream<Arguments> invalidNewCategoryRequests() {
        return Stream.of(
                Arguments.of(new NewCategoryRequest("")),
                Arguments.of(new NewCategoryRequest(null)),
                Arguments.of(new NewCategoryRequest("   "))
        );
    }

    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MODERATOR_USER)
    @Test
    public void shouldReturn409WhenCreateNewCategoryWithExistingName() throws Exception {

        // given
        given(categoryMapper.mapNewCategoryRequestToCategoryDTO(any(NewCategoryRequest.class))).willReturn(new CategoryDTO());
        given(categoryService.createCategory(any(CategoryDTO.class))).willThrow(AlreadyExistsException.class);

        // when
        var request = post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(new NewCategoryRequest("test")));

        // then
        mockMvc.perform(request).andExpect(status().isConflict());
    }

    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MODERATOR_USER)
    @Test
    public void shouldReturn202WhenMarkCategoryAsDeleted() throws Exception {

        // given
        willDoNothing().given(categoryService).markCategoryAsDeleted(anyLong());

        // when
        var request = delete("/categories/{id}", 1);

        // then
        mockMvc.perform(request)
                .andExpect(status().isAccepted());
    }

    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MODERATOR_USER)
    @Test
    public void shouldReturn200WhenSuccessEditCategory() throws Exception {

        // given
        var editCategoryRequest = new EditCategoryRequest();
        editCategoryRequest.setId(1L);
        editCategoryRequest.setName("test");
        given(categoryMapper.mapEditCategoryRequestToCategoryDTO(any(EditCategoryRequest.class))).willReturn(new CategoryDTO());
        given(categoryService.modifyCategory(any(CategoryDTO.class))).willReturn(new Category());
        given(categoryMapper.mapToDTO(any(Category.class))).willReturn(new CategoryDTO());

        // when
        var request = patch("/categories/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editCategoryRequest));

        // then
        mockMvc.perform(request)
                .andExpect(status().isOk());
    }

    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MODERATOR_USER)
    @Test
    public void shouldReturn400WhenEditCategoryWithInvalidBodyId() throws Exception {
        // given
        var editCategoryRequest = new EditCategoryRequest();
        editCategoryRequest.setId(1L);
        editCategoryRequest.setName("test");

        // when
        var request = patch("/categories/{id}", 2137)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editCategoryRequest));
        // then
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails(userDetailsServiceBeanName = "testUserDetailsService", value = MODERATOR_USER)
    @Test
    public void shouldReturn409WhenEditCategoryWithExistedCategoryName() throws Exception {
        // given
        var editCategoryRequest = new EditCategoryRequest();
        editCategoryRequest.setId(1L);
        editCategoryRequest.setName("test");
        given(categoryService.modifyCategory(any())).willThrow(AlreadyExistsException.class);

        // when
        var request = patch("/categories/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(editCategoryRequest));
        // then
        mockMvc.perform(request)
                .andExpect(status().isConflict());
    }


}
