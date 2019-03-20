package com.yj.clothsdemo.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.yj.clothsdemo.R
import kotlinx.android.synthetic.main.item_put.view.*

/**
 * desc:
 * time: 2019/3/20
 * @author yinYin
 */
class PutAdapter:BaseQuickAdapter<Any,BaseViewHolder>(R.layout.item_put) {
    override fun convert(helper: BaseViewHolder?, item: Any?) {
        helper?.itemView?.apply {
            tv_time.text="0305上\n01"
            tv_num.text="01"
            tv_open.isEnabled=true
            tv_sure.isEnabled=true
        }
        helper?.addOnClickListener(R.id.tv_open)
        helper?.addOnClickListener(R.id.tv_sure)
    }
}