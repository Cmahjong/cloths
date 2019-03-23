package com.yj.clothsdemo.adapter

import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import com.yj.service.response.Data4
import kotlinx.android.synthetic.main.item_put_recode.view.*

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
class PutRecodeAdapter:BaseQuickAdapter<Data4,BaseViewHolder>(R.layout.item_put_recode) {
    override fun convert(helper: BaseViewHolder?, item: Data4?) {
        helper?.itemView?.apply {
            tv_time.text=(item?.operateTime?:"")+"\n"+(item?.pieceNumber?:"")
            tv_num.text=item?.boxNumber?:""
            tv_area.text=(item?.createName?:"")+"\n"+(item?.finalName?:"")
            if (item?.same == true) {
                tv_area.setTextColor(ContextCompat.getColor(context, R.color.main_color))
            } else {
                tv_area.setTextColor(ContextCompat.getColor(context,R.color.red))
            }
        }
    }
}