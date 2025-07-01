package com.example.readerapp.domain.pages.model

enum class PageContentType(val id: String) {
    Page(id = "page"),
    Section(id = "section"),
    Text(id = "text"),
    Image(id = "image"),
    Unsupported(id = "");

    companion object {

        fun find(typeId: String): PageContentType {
            return entries.find { type ->
                type.id == typeId
            } ?: Unsupported
        }

        fun isSupported(typeId: String): Boolean {
            return find(typeId) != Unsupported
        }
    }
}