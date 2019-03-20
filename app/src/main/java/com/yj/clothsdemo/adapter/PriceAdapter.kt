package com.yj.clothsdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import kotlinx.android.synthetic.main.item_price.view.*

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
class PriceAdapter:BaseQuickAdapter<Any,BaseViewHolder>(R.layout.item_price) {
    override fun convert(helper: BaseViewHolder?, item: Any?) {
        helper?.itemView?.apply {
            tv_time.text="0305上\n01"
            tv_area.text="新都区"
            tv_handle.isEnabled=true
        }
        helper?.addOnClickListener(R.id.tv_handle)
        helper?.addOnClickListener(R.id.tv_sure)
    }
}