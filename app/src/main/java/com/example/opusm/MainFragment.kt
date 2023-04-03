package com.example.opusm

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.opusm.databinding.FragmentMainBinding
import com.google.android.material.tabs.TabLayout


class MainFragment : Fragment() {

    lateinit var binding:FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)

        val viewPager: ViewPager = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        // create adapter
        val adapter = ViewPagerAdapter(childFragmentManager)

        // add fragments to adapter
        adapter.addFragment(AssetFragment(), "구매")
        adapter.addFragment(ActivityFragment(), "활동")

        // set adapter to view pager
        viewPager.adapter = adapter

        // connect tab layout with view pager
        tabLayout.setupWithViewPager(viewPager)

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)


        val btn_swap = binding.btnSwap
        val tv_clip_hash = binding.tvClipHash

        btn_swap.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SwapFragment())
                .addToBackStack(null)
                .commit()
        }

        tv_clip_hash.setOnClickListener {
            val clipboard = requireContext().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", tv_clip_hash.text.toString())
            clipboard.setPrimaryClip(clip)

            Toast.makeText(requireContext(), "클립보드에 복사되었습니다.", Toast.LENGTH_SHORT).show()
        }

       
}


    // inner class for View Pager Adapter
    inner class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        private val fragmentList: MutableList<Fragment> = ArrayList()
        private val titleList: MutableList<String> = ArrayList()

        override fun getItem(position: Int): Fragment {
            return fragmentList[position]
        }

        override fun getCount(): Int {
            return fragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }

    }
}
