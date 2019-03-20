package com.yj.clothsdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import kotlinx.android.synthetic.main.item_take.view.*

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
class TakeAdapter:BaseQuickAdapter<Any,BaseViewHolder>(R.layout.item_take) {
    override fun convert(helper: BaseViewHolder?, item: Any?) {
        helper?.itemView?.apply {
            tv_num.text="01"

            tv_open.isEnabled=true
        }
        helper?.addOnClickListener(R.id.tv_open)
    }
}