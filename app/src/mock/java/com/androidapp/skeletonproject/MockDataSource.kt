package com.androidapp.skeletonproject

import android.content.Context
import androidx.annotation.RawRes
import com.androidapp.skeletonproject.model.apientity.UserApiEntity
import com.androidapp.skeletonproject.network.BaseResponse
import com.androidapp.skeletonproject.services.IDataSource
import com.androidapp.skeletonproject.utils.GsonDateTimeConverter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.Single
import org.joda.time.DateTime
import java.io.BufferedReader
import java.io.InputStreamReader

class MockDataSource(private val context: Context) : IDataSource {

    private var gson: Gson

    init {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(DateTime::class.java, GsonDateTimeConverter())
        gson = builder.create()
    }

    /**
     * Parse json file containing mocked data and return it
     */
    private fun <T : Any> parseJsonResource(@RawRes resId: Int, classType: Class<T>): T {
        val buffer = StringBuilder()
        val inputStream = context.resources.openRawResource(resId)
        BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            .use {
                val list = it.readLines()
                buffer.append(list.joinToString("\n"))
            }
        return gson.fromJson(buffer.toString(), classType)
    }

    //Todo: implement here each mocked api route

    override fun getUserDetails(userId:String): Single<BaseResponse<UserApiEntity>> {
        val value = parseJsonResource(
            R.raw.get_profile,
            UserApiEntity::class.java
        )
        val fakeResponse: BaseResponse<UserApiEntity> =
            BaseResponse<UserApiEntity>(code = 200, message = "success", data = value)
        return Observable.fromArray(fakeResponse).singleOrError()
    }
}

