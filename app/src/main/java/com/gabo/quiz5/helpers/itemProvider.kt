package com.gabo.quiz5.helpers

import com.gabo.quiz5.model.Item

object itemProvider {
    val listPin = listOf<Item.GroupItems.FieldModel>(
        Item.GroupItems.FieldModel(12341,"PIN", "number","number",true,true,"jemala"),
        Item.GroupItems.FieldModel(12341,"CONFIRM PIN", "number","number",true,true,"jemala")
    )
}