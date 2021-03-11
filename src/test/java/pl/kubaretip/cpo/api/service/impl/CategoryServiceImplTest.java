package pl.kubaretip.cpo.api.service.impl;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.kubaretip.cpo.api.domain.Category;
import pl.kubaretip.cpo.api.dto.CategoryDTO;
import pl.kubaretip.cpo.api.exception.AlreadyExistsException;
import pl.kubaretip.cpo.api.exception.NotFoundException;
import pl.kubaretip.cpo.api.repository.CategoryRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;


    @Test
    public void shouldReturnAllCategories() {

        // given
        given(categoryRepository.findAll()).willReturn(Arrays.asList(new Category(), new Category(), new Category()));

        // when
        var result = categoryService.getAllCategories();

        // then
        assertThat(result, hasSize(3));
        then(categoryRepository).should(times(1)).findAll();
    }


    @Test
    public void shouldReturnCategoryById() {

        // given
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(new Category()));

        // when
        var result = categoryService.getCategoryById(1);

        // then
        assertThat(result, is(notNullValue()));
        then(categoryRepository).should(times(1)).findById(anyLong());
    }

    @Test
    public void shouldCreateNewCategory() {

        // given
        var categoryDTO = new CategoryDTO("test");

        given(categoryRepository.existsByName(anyString())).willReturn(false);
        given(categoryRepository.save(ArgumentMatchers.any(Category.class))).willAnswer(returnsFirstArg());

        // when
        var category = categoryService.createCategory(categoryDTO);

        // then
        assertThat(category, is(notNullValue()));
        assertThat(category.getName(), containsStringIgnoringCase("test"));
        assertThat(category.getName(), Matchers.startsWith("T"));
        then(categoryRepository).should(times(1)).save(ArgumentMatchers.any(Category.class));
        then(categoryRepository).should(times(1)).existsByName("test");
    }

    @Test
    public void exceptionShouldBeThrownWhenCategoryWithNameAlreadyExists() {

        // given
        var categoryDTO = new CategoryDTO("test");

        given(categoryRepository.existsByName(anyString())).willReturn(true);

        // when + then
        assertThrows(AlreadyExistsException.class, () -> categoryService.createCategory(categoryDTO));
        then(categoryRepository).should(times(0)).save(ArgumentMatchers.any(Category.class));
    }


    @Test
    public void shouldMarkCategoryAsDeleted() {

        // given
        var mockCategory = mock(Category.class);
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(mockCategory));
        given(categoryRepository.save(ArgumentMatchers.any(Category.class))).willAnswer(returnsFirstArg());
        var inOrder = inOrder(mockCategory, categoryRepository);

        // when
        categoryService.markCategoryAsDeleted(2);

        // then
        then(mockCategory).should(inOrder).setDeleted(true);
        then(categoryRepository).should(inOrder).save(mockCategory);
        then(categoryRepository).should(times(1)).save(mockCategory);
        then(mockCategory).should(times(1)).setDeleted(true);
    }

    @Test
    public void exceptionShouldBeThrowWhenModifyNoteExistedCategory() {

        // given
        var categoryDTO = new CategoryDTO(1L);

        given(categoryRepository.findById(anyLong())).willReturn(Optional.empty());

        // when + then
        assertThrows(NotFoundException.class, () -> categoryService.modifyCategory(categoryDTO));
        then(categoryRepository).should(times(0)).save(ArgumentMatchers.any(Category.class));

    }

    @Test
    public void exceptionShouldBeThrownWhenAlreadyExistsCategoryWithName() {

        // given
        var categoryName = "test";
        var category = new Category();
        category.setId(1L);
        category.setName(categoryName);

        var newCategoryData = new CategoryDTO(2L, categoryName);
        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));
        given(categoryRepository.findByName(anyString())).willReturn(Optional.of(category));

        // when + then
        assertThrows(AlreadyExistsException.class, () -> categoryService.modifyCategory(newCategoryData));
        then(categoryRepository).should(times(0)).save(category);
    }

    @Test
    public void shouldUpdateCategory() {

        // given
        var category = new Category();
        category.setId(1L);
        category.setName("v1");

        var categoryDTO = new CategoryDTO(1L, "v2");

        given(categoryRepository.findById(anyLong())).willReturn(Optional.of(category));
        given(categoryRepository.findByName(anyString())).willReturn(Optional.empty());
        given(categoryRepository.save(category)).willAnswer(returnsFirstArg());

        // when
        var result = categoryService.modifyCategory(categoryDTO);

        // then
        assertThat(result, is(notNullValue()));
        assertThat(result.getId(), is(equalTo(1L)));
        assertThat(result.getName(), is(not(equalTo("v2"))));
        then(categoryRepository).should(times(1)).save(category);
    }

}
