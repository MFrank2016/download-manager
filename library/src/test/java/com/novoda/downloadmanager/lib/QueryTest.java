package com.novoda.downloadmanager.lib;

import android.content.ContentResolver;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

public class QueryTest {

    @Mock
    private Uri uri;

    @Mock
    private ContentResolver resolver;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
    }

    @Test
    public void givenBatchIdsWhenTheQueryIsCreatedThenTheWhereStatementIsCorrect() {
        new Query().setFilterByBatchId(1, 2, 3).runQuery(resolver, null, uri);

        verify(resolver).query(any(Uri.class), any(String[].class), stringArgumentCaptor.capture(), any(String[].class), anyString());

        assertThat(stringArgumentCaptor.getValue()).contains(Downloads.Impl.COLUMN_BATCH_ID + " IN (1,2,3)");
    }

    @Test
    public void givenNoBatchIdsWhenTheQueryIsCreatedThenTheWhereStatementContainsNoBatchIdPredicate() {
        new Query().runQuery(resolver, null, uri);

        verify(resolver).query(any(Uri.class), any(String[].class), stringArgumentCaptor.capture(), any(String[].class), anyString());

        assertThat(stringArgumentCaptor.getValue()).doesNotContain(Downloads.Impl.COLUMN_BATCH_ID + " IN ");
    }
}