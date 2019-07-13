package com.yj.clothsdemo.adapter

import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import com.yj.service.response.OrderBox
import kotlinx.android.synthetic.main.item_take.view.*

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
class Take1Adapter:BaseQuickAdapter<OrderBox,BaseViewHolder>(R.layout.item_take1) {
    override fun convert(helper: BaseViewHolder?, item: OrderBox?) {
        helper?.itemView?.apply {
            tv_num.text = item?.boxNumber ?: ""
            //0是关
            //1是开
            if (item?.onOff == "0") {
                tv_open.isEnabled = true
                tv_status.text="关"
                tv_num.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL)
                tv_status.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL)
                tv_num.setTextColor(ContextCompat.getColor(context,R.color.main_color))
                tv_status.setTextColor(ContextCompat.getColor(context,R.color.main_color))
            } else {
                tv_status.text="开"
                tv_open.isEnabled = false
                tv_num.setTextColor(ContextCompat.getColor(context,R.color.main_color))
                tv_num.setTypeface(Typeface.SANS_SERIF, Typeface.NORMAL)
                tv_status.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)
                tv_status.setTextColor(ContextCompat.getColor(context,R.color.red))
            }

        }
        helper?.addOnClickListener(R.id.tv_open)
    }
}