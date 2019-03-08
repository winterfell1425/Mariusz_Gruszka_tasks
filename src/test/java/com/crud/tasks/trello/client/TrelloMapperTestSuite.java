package com.crud.tasks.trello.client;

import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TrelloMapperTestSuite {
    @Autowired
    TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        TrelloListDto trelloListBlue = new TrelloListDto(
                "300",
                "BlueList",
                false);
        TrelloListDto trelloListGreen = new TrelloListDto(
                "301",
                "GreenList",
                false);

        List<TrelloListDto> trelloListDtoList0 = new ArrayList<>();
        trelloListDtoList0.add(trelloListBlue);
        trelloListDtoList0.add(trelloListGreen);

        TrelloBoardDto trelloBoardBlue = new TrelloBoardDto(
                "1",
                "TrelloBoardBlue",
                trelloListDtoList0);

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardBlue);
        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDtos);
        //Then
        assertEquals("1", trelloBoards.get(0).getId());
        assertEquals("TrelloBoardBlue", trelloBoards.get(0).getName());
        assertEquals("301", trelloBoards.get(0).getLists().get(1).getId());
        assertEquals("BlueList", trelloBoards.get(0).getLists().get(0).getName());
    }
    @Test
    public void testMapToBoardsDto() {
        //Given
        TrelloList trelloListYellow = new TrelloList(
                "400",
                "YellowList",
                true);
        TrelloList trelloListGrey = new TrelloList(
                "401",
                "GreyList",
                true);

        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(trelloListYellow);
        trelloLists.add(trelloListGrey);

        TrelloBoard trelloBoardPink = new TrelloBoard(
                "13",
                "TrelloBoardPink",
                trelloLists);

        List<TrelloBoard> trelloBoards = new ArrayList<>();
        trelloBoards.add(trelloBoardPink);
        //When
        List<TrelloBoardDto> trelloBoardDtoList = trelloMapper.mapToBoardsDto(trelloBoards);
        //Then
        assertEquals("13", trelloBoardDtoList.get(0).getId());
        assertEquals("TrelloBoardPink", trelloBoardDtoList.get(0).getName());
        assertEquals("400", trelloBoardDtoList.get(0).getLists().get(0).getId());
        assertEquals("YellowList", trelloBoardDtoList.get(0).getLists().get(0).getName());
        assertEquals(true, trelloBoardDtoList.get(0).getLists().get(1).isClosed());
    }
    @Test
    public void testMapToList() {
        //Given
        TrelloListDto trelloListBlue = new TrelloListDto(
                "100",
                "BlueList",
                false);
        TrelloListDto trelloListGreen = new TrelloListDto(
                "101",
                "GreenList",
                false);

        List<TrelloListDto> trelloListDtoList = new ArrayList<>();
        trelloListDtoList.add(trelloListBlue);
        trelloListDtoList.add(trelloListGreen);
        //When
        List<TrelloList> trelloLists = trelloMapper.mapToList(trelloListDtoList);
        //Then
        assertEquals("100", trelloLists.get(0).getId());
        assertEquals("GreenList", trelloLists.get(1).getName());
        assertEquals(false, trelloLists.get(1).isClosed());
    }
    @Test
    public void testMapToListDto() {
        //Given
        TrelloList trelloListRed = new TrelloList(
                "200",
                "RedList",
                true);
        TrelloList trelloListWhite = new TrelloList(
                "201",
                "WhiteList",
                true);

        List<TrelloList> trelloListArrayList = new ArrayList<>();
        trelloListArrayList.add(trelloListRed);
        trelloListArrayList.add(trelloListWhite);
        //When
        List<TrelloListDto> trelloDtoLists = trelloMapper.mapToListDto(trelloListArrayList);
        //Then
        assertEquals("200", trelloDtoLists.get(0).getId());
        assertEquals("RedList", trelloDtoLists.get(0).getName());
        assertEquals(true, trelloDtoLists.get(1).isClosed());
    }
    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard trelloCard = new TrelloCard(
                "Easy task",
                "Very easy",
                "bottom",
                "3333");
        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);
        //Then
        assertEquals("Easy task", trelloCardDto.getName());
        assertEquals("Very easy", trelloCardDto.getDescription());
        assertEquals("bottom", trelloCardDto.getPos());
        assertEquals("3333", trelloCardDto.getListId());
    }
    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Blue task",
                "Very blue",
                "top",
                "1111");
        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);
        //Then
        assertEquals("Blue task", trelloCard.getName());
        assertEquals("Very blue", trelloCard.getDescription());
        assertEquals("top", trelloCard.getPos());
        assertEquals("1111", trelloCard.getListId());
    }
}